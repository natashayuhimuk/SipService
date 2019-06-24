import React, { useState, useEffect } from "react";

import Table from "../Table";
import { Modal } from "../Common";
import Button from "@material-ui/core/Button";
import CardAddUser from "./CardAddUser";
import CardDeleteUsers from "./CardDeleteUsers";
import CardEditUser from "./CardEditUser";
import { tariffService, userService } from "../../services";
import CardBlackListUser from "./CardBlackListUser";

const Users = ({ openAlert, startFetch, finishFetch }) => {
  const [data, setData] = useState([]);
  const [modal, setModal] = useState("");
  const [payload, setPayload] = useState([]);
  const [tariffs, setTariffs] = useState([]);

  useEffect(() => {
    getData();
    return finishFetch;
  }, []);
  useEffect(() => getTariffs(), []);

  const rows = [
    { id: "phoneNumber", label: "Phone Number" },
    { id: "firstName", label: "First Name" },
    { id: "lastName", label: "Last Name" },
    { id: "secondName", label: "Second Name" },
    { id: "tariffName", label: "Tariff" },
    { id: "balance", label: "Balance" },
    { id: "role", label: "Role" },
    { id: "blackList", label: "Black List" }
  ];

  const buttons = {
    main: [{ title: "Add", icon: "add", func: () => clickButton("Add") }],
    selected: [
      { title: "Delete", icon: "delete", func: n => clickButton("Delete", n) }
    ],
    oneSelected: [
      { title: "Edit", icon: "edit", func: n => clickButton("Edit", n) }
    ]
  };

  const getTariffs = () => {
    tariffService.getTariffs().then(({ tariffs, message, success }) => {
      if (success) {
        setTariffs(tariffs);
      } else {
        openAlert(message, success);
      }
    });
  };

  const parseUserList = users => {
    const usersWithBL = users.user.map(user => ({
      ...user,
      tariffName: user.tariff.name,
      blackList: (
        <Button
          onClick={event => {
            event.stopPropagation();
            setModal("BlackList");
            setPayload(user.phoneNumber);
          }}
        >
          SHOW
        </Button>
      )
    }));
    setData(usersWithBL);
  };

  const getData = () => {
    startFetch();
    userService.getAllUsers().then(({ users, message, success }) => {
      if (success) {
        parseUserList(users);
      } else {
        openAlert(message, success);
      }
      finishFetch();
    });
  };

  const addData = person => {
    userService.createUser(person).then(({ users, success, message }) => {
      if (success) {
        parseUserList(users);
      }
      openAlert(message, success);
    });
  };

  const editData = person => {
    userService
      .updateUserByAdmin(person)
      .then(({ users, success, message }) => {
        if (success) {
          parseUserList(users);
        }
        openAlert(message, success);
      });
  };

  const deleteData = selected => {
    userService.deleteUser(selected).then(({ users, success, message }) => {
      if (success) {
        parseUserList(users);
      }
      openAlert(message, success);
    });
  };

  const clickButton = (modal, payload = []) => {
    setModal(modal);
    setPayload(payload);
  };

  return (
    <div>
      <Table
        rows={rows}
        array={data}
        buttons={buttons}
        title={"Users"}
        fieldId={"phoneNumber"}
      />
      <Modal
        open={modal !== ""}
        content={
          modal === "Delete" ? (
            <CardDeleteUsers
              setModal={setModal}
              payload={payload}
              data={data}
              func={deleteData}
              fieldId={"phoneNumber"}
            />
          ) : modal === "Add" ? (
            <CardAddUser func={addData} setModal={setModal} tariffs={tariffs} />
          ) : modal === "Edit" ? (
            <CardEditUser
              func={editData}
              setModal={setModal}
              data={data.find(item => item.phoneNumber === payload[0])}
              tariffs={tariffs}
            />
          ) : modal === "BlackList" ? (
            <CardBlackListUser setModal={setModal} payload={payload} />
          ) : null
        }
      />
    </div>
  );
};

export default Users;

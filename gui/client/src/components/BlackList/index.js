import React, { useState, useEffect } from "react";

import Table from "../Table";
import { Modal } from "../Common";
import CardDeleteNumber from "./CardDeleteNumber";
import CardAddNumber from "./CardAddNumber";
import { blackListService } from "../../services";

const BlackList = ({ openAlert, startFetch, finishFetch }) => {
  const [data, setData] = useState([]);
  const [modal, setModal] = useState("");
  const [payload, setPayload] = useState([]);

  useEffect(() => {
    getData();
    return finishFetch;
  }, []);

  const rows = [{ id: "phoneNumber", label: "Phone number" }];

  const buttons = {
    main: [{ title: "Add", icon: "add", func: () => clickButton("Add") }],
    selected: [
      { title: "Delete", icon: "delete", func: n => clickButton("Delete", n) }
    ]
  };

  const getData = () => {
    startFetch();
    blackListService.getAll().then(({ blacklist, message, success }) => {
      if (success && blacklist) {
        const phoneNumbers = blacklist.number.map(phoneNumber => ({
          phoneNumber
        }));
        setData(phoneNumbers);
      } else {
        openAlert(message, success);
      }
      finishFetch();
    });
  };

  const addData = number => {
    blackListService
      .addNumber(number)
      .then(({ blacklist, message, success }) => {
        if (success && blacklist) {
          const phoneNumbers = blacklist.number.map(phoneNumber => ({
            phoneNumber
          }));
          setData(phoneNumbers);
        }
        openAlert(message, success);
      });
  };

  const deleteData = numbers => {
    blackListService
      .deleteNumbers(numbers)
      .then(({ blacklist, message, success }) => {
        if (success && blacklist) {
          const phoneNumbers = blacklist.number.map(phoneNumber => ({
            phoneNumber
          }));
          setData(phoneNumbers);
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
        title={"Black List"}
        fieldId={"phoneNumber"}
      />
      <Modal
        open={modal !== ""}
        content={
          modal === "Delete" ? (
            <CardDeleteNumber
              setModal={setModal}
              payload={payload}
              data={data}
              func={deleteData}
              fieldId={"phoneNumber"}
            />
          ) : modal === "Add" ? (
            <CardAddNumber func={addData} setModal={setModal} />
          ) : null
        }
      />
    </div>
  );
};

export default BlackList;

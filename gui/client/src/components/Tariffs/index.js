import React, { useState, useEffect } from "react";

import Table from "../Table";
import { Modal } from "../Common";
import CardAddTariff from "./CardAddTariff";
import CardDeleteTariff from "./CardDeleteTariff";
import CardEditTariff from "./CardEditTariff";
import { tariffService } from "../../services";

const Tariffs = ({ openAlert, startFetch, finishFetch }) => {
  const [data, setData] = useState([]);
  const [modal, setModal] = useState("");
  const [payload, setPayload] = useState([]);

  useEffect(() => {
    getData();
    return finishFetch;
  }, []);

  const rows = [
    { id: "name", label: "Tariff Name" },
    { id: "cost", label: "Cost" }
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

  const getData = () => {
    startFetch();
    tariffService.getTariffs().then(({ tariffs, message, success }) => {
      if (success) {
        setData(tariffs);
      } else {
        openAlert(message, success);
      }
      finishFetch();
    });
  };

  const addData = tariff => {
    tariffService.addTariff(tariff).then(({ tariffs, message, success }) => {
      if (success) {
        setData(tariffs);
      }
      openAlert(message, success);
    });
  };

  const editData = tariff => {
    tariffService.updateTariff(tariff).then(({ tariffs, message, success }) => {
      if (success) {
        setData(tariffs);
      }
      openAlert(message, success);
    });
  };

  const deleteData = tariffs => {
    tariffService
      .deleteTariffs(tariffs)
      .then(({ tariffs, message, success }) => {
        if (success) {
          setData(tariffs);
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
        title={"Tariff"}
        fieldId={"name"}
      />
      <Modal
        open={modal !== ""}
        content={
          modal === "Delete" ? (
            <CardDeleteTariff
              setModal={setModal}
              payload={payload}
              data={data}
              func={deleteData}
              fieldId={"name"}
            />
          ) : modal === "Add" ? (
            <CardAddTariff func={addData} setModal={setModal} />
          ) : modal === "Edit" ? (
            <CardEditTariff
              func={editData}
              setModal={setModal}
              data={data.find(item => item.name === payload[0])}
            />
          ) : null
        }
      />
    </div>
  );
};

export default Tariffs;

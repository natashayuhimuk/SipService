import React, { useEffect, useState } from "react";

import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";

import TextFieldList from "../Common/TextFieldList";
import { MESSAGE, VALID } from "../../config";

const styles = theme => ({
  spacer: {
    flex: "1 1 100%"
  },
  content: {
    padding: `0 ${theme.spacing.unit * 2}px`,
    maxHeight: "300px",
    overflowY: "auto"
  },
  header: {
    padding: theme.spacing.unit * 2
  }
});

const CardEdit = ({ classes, setModal, func, data, tariffs }) => {
  const [firstName, setFirstName] = useState(data.firstName);
  const [lastName, setLastName] = useState(data.lastName);
  const [secondName, setSecondName] = useState(data.secondName);
  const [tariffName, setTariffName] = useState(data.tariff.name);
  const [role, setRole] = useState(data.role);
  const [balance, setBalance] = useState(data.balance);
  const [roles, setRoles] = useState([]);
  const [valid, setValid] = useState([]);

  useEffect(() => getRoles(), []);
  useEffect(() => setValidTrue(), []);

  const getRoles = () => {
    setRoles(["ADMIN", "USER"]);
  };

  const setValidTrue = () => {
    const valid = conf.map(() => true);
    setValid(valid);
  };

  const onValid = () => {
    const valid = conf.map(({ value, regexp = "" }) =>
      new RegExp(regexp).test(value)
    );
    setValid(valid);
    return valid.every(item => item);
  };

  const conf = [
    {
      title: "First Name",
      value: firstName,
      setValue: setFirstName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Last Name",
      value: lastName,
      setValue: setLastName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Second Name",
      value: secondName,
      setValue: setSecondName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Tariff",
      type: "select",
      value: tariffName,
      setValue: setTariffName,
      list: tariffs
    },
    {
      title: "Role",
      type: "select",
      value: role,
      setValue: setRole,
      list: roles
    },
    {
      title: "Balance",
      value: balance,
      setValue: setBalance,
      regexp: VALID.number,
      message: MESSAGE.ERROR.number
    }
  ];

  const onSubmit = () => {
    if (onValid()) {
      const newUser = {
        phoneNumber: data.phoneNumber,
        firstName,
        lastName,
        secondName,
        tariff: tariffName,
        newRole: role,
        balance
      };
      setModal("");
      func(newUser);
    }
  };

  return (
    <Card>
      <CardHeader title="Edit User" className={classes.header} />
      <CardContent className={classes.content}>
        <TextFieldList conf={conf} valid={valid} />
      </CardContent>
      <CardActions>
        <div className={classes.spacer} />
        <Button size="small" color="primary" onClick={onSubmit}>
          Ok
        </Button>
        <Button size="small" color="secondary" onClick={() => setModal("")}>
          Cancel
        </Button>
      </CardActions>
    </Card>
  );
};

export default withStyles(styles)(CardEdit);

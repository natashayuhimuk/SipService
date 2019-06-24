import React, { useEffect, useState } from "react";

import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";

import { TextFieldList } from "../Common";
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

const CardAdd = ({ classes, setModal, tariffs, func }) => {
  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [secondName, setSecondName] = useState("");
  const [tariffName, setTariffName] = useState("");
  const [balance, setBalance] = useState(0);

  const [valid, setValid] = useState([]);

  useEffect(() => setValidTrue(), []);

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
      title: "Phone Number",
      value: phoneNumber,
      setValue: setPhoneNumber,
      regexp: VALID.phoneNumber,
      message: MESSAGE.ERROR.phoneNumber
    },
    {
      title: "Password",
      type: "password",
      value: password,
      setValue: setPassword,
      regexp: VALID.password,
      message: MESSAGE.ERROR.password
    },
    {
      title: "Repeat Password",
      type: "password",
      value: repeatPassword,
      setValue: setRepeatPassword,
      regexp: VALID.password,
      message: MESSAGE.ERROR.rePassword
    },
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
        phoneNumber: phoneNumber.replace(/\D+/g, ""),
        password,
        firstName,
        lastName,
        secondName,
        tariff: tariffName,
        balance
      };
      func(newUser);
      setModal("");
    }
  };

  return (
    <Card>
      <CardHeader title="Add User" className={classes.header} />
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

export default withStyles(styles)(CardAdd);

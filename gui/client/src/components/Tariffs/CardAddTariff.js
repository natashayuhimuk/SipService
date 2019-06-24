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

const CardAdd = ({ classes, setModal, func }) => {
  const [name, setName] = useState("");
  const [cost, setCost] = useState("0");

  const [valid, setValid] = useState([]);

  useEffect(() => setValidTrue(), []);

  const conf = [
    {
      title: "Name",
      value: name,
      setValue: setName,
      regexp: VALID.name,
      message: MESSAGE.ERROR.name
    },
    {
      title: "Cost",
      value: cost,
      setValue: setCost,
      regexp: VALID.number,
      message: MESSAGE.ERROR.number
    }
  ];

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

  const onSubmit = () => {
    if (onValid()) {
      func({
        name,
        cost
      });
      setModal("");
    }
  };

  return (
    <Card>
      <CardHeader title="Add Tariff" className={classes.header} />
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

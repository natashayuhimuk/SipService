import React, { useState } from "react";
import NumberFormat from "react-number-format";

import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";
import TextField from "@material-ui/core/TextField";
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
  const [phoneNumber, setPhoneNumber] = useState("");
  const [valid, setValid] = useState(true);

  const onValid = () => {
    const _valid = new RegExp(VALID.phoneNumber).test(phoneNumber);
    setValid(_valid);
    return _valid;
  };

  const onSubmit = () => {
    if (onValid()) {
      const number = phoneNumber.replace(/\D+/g, "");
      func(number);
      setModal("");
    }
  };

  return (
    <Card>
      <CardHeader title="Add Number" className={classes.header} />
      <CardContent className={classes.content}>
        <NumberFormat
          type="tel"
          label="Phone Number"
          customInput={TextField}
          value={phoneNumber}
          onChange={e => setPhoneNumber(e.target.value)}
          placeholder="+375 (##) ###-##-##"
          format="+375 (##) ###-##-##"
          mask="_"
          helperText={!valid && MESSAGE.ERROR.password}
          autoFocus={true}
          error={!valid}
          onFocus={() => setValid(true)}
          fullWidth={true}
        />
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

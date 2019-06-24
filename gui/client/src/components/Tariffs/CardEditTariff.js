import React, { useState } from "react";

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

const CardEdit = ({ classes, setModal, func, data }) => {
  const [cost, setCost] = useState(data.cost);
  const [validCost, setValidCost] = useState(true);

  const onValid = () => {
    const valid = new RegExp(VALID.number).test(cost);
    setValidCost(valid);
    return valid;
  };

  const onSubmit = () => {
    if (onValid()) {
      func({
        name: data.name,
        cost
      });
      setModal("");
    }
  };

  return (
    <Card>
      <CardHeader
        title={`Edit Tariff ( ${data.name} )`}
        className={classes.header}
      />
      <CardContent className={classes.content}>
        <TextField
          type="text"
          label="Cost"
          value={cost}
          onChange={e => setCost(e.target.value)}
          margin="dense"
          error={!validCost}
          helperText={!validCost && MESSAGE.ERROR.number}
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

export default withStyles(styles)(CardEdit);

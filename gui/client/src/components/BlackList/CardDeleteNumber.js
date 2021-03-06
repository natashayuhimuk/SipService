import React, { useEffect, useState } from "react";
import { commonService } from "../../services";

import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";

const styles = theme => ({
  spacer: {
    flex: "1 1 100%"
  },
  content: {
    padding: theme.spacing.unit * 1,
    display: "flex",
    justifyContent: "center",
    maxHeight: "300px",
    overflowY: "auto"
  },
  header: {
    padding: theme.spacing.unit * 2
  },
  listItem: {
    padding: theme.spacing.unit * 1
  }
});

const CardDelete = ({
  classes,
  setModal,
  func,
  payload = [],
  data = [],
  fieldId
}) => {
  const [numbers, setNumbers] = useState([]);

  useEffect(() => {
    const numbers = [];
    payload.forEach(id => {
      for (let i = 0; i < data.length; i++) {
        if (data[i][fieldId] === id) {
          numbers.push(data[i]["phoneNumber"]);
          return;
        }
      }
    });
    setNumbers(numbers);
  }, []);

  return (
    <Card>
      <CardHeader title="Delete Number" className={classes.header} />
      <CardContent className={classes.content}>
        <List>
          {numbers.map(number => (
            <ListItem key={number} className={classes.listItem}>
              <ListItemText primary={commonService.parseNumber(number)} />
            </ListItem>
          ))}
        </List>
      </CardContent>
      <CardActions>
        <div className={classes.spacer} />
        <Button
          size="small"
          color="primary"
          onClick={() => {
            func(payload);
            setModal("");
          }}
        >
          Ok
        </Button>
        <Button size="small" color="secondary" onClick={() => setModal("")}>
          Cancel
        </Button>
      </CardActions>
    </Card>
  );
};

export default withStyles(styles)(CardDelete);

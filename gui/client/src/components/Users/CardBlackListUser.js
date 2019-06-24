import React, { useEffect, useState } from "react";

import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";

import { blackListService, commonService } from "../../services";

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

const CardBlackListUser = ({ classes, setModal, payload = "" }) => {
  const [data, setData] = useState([]);

  useEffect(() => getData(), []);

  const getData = () => {
    blackListService
      .getByNumber(payload)
      .then(({ blacklist, message, success }) => {
        if (success && blacklist) {
          setData(blacklist.number);
        }
      });
  };

  return (
    <Card>
      <CardHeader title="Black List" className={classes.header} />
      <CardContent className={classes.content}>
        <List>
          {data.map(blackList => (
            <ListItem key={blackList} className={classes.listItem}>
              <ListItemText primary={commonService.parseNumber(blackList)} />
            </ListItem>
          ))}
        </List>
      </CardContent>
      <CardActions>
        <div className={classes.spacer} />
        <Button size="small" color="secondary" onClick={() => setModal("")}>
          Cancel
        </Button>
      </CardActions>
    </Card>
  );
};

export default withStyles(styles)(CardBlackListUser);

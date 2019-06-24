import React, { useEffect, useState } from "react";

import IconButton from "@material-ui/core/IconButton";
import Icon from "@material-ui/core/Icon";
import { withStyles } from "@material-ui/core/styles";
import { statService } from "../../services";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Switch from "@material-ui/core/Switch";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import TableHead from "@material-ui/core/TableHead";
import Table from "@material-ui/core/Table";
import TableRow from "@material-ui/core/TableRow";
import Toolbar from "@material-ui/core/Toolbar";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";

const styles = theme => ({
  spacer: {
    flex: "1 1 100%"
  },
  title: {
    flex: "0 0 auto"
  },
  actions: {
    color: theme.palette.text.secondary,
    display: "flex",
    flexDirection: "row"
  },
  root: {
    width: "100%"
  },
  tableWrapper: {
    overflowX: "auto"
  },
  formControl: {
    margin: theme.spacing.unit,
    minWidth: 80
  }
});

const Statistics = ({ classes, openAlert, startFetch, finishFetch }) => {
  const [statistics, setStatistics] = useState([]);
  const [autoUpdate, setAutoUpdate] = useState(false);
  const [timer, setTimer] = useState(10000);
  useEffect(() => {
    getStatistics();
    return finishFetch;
  }, []);
  useEffect(() => changeTimer(), [autoUpdate, timer]);

  const changeTimer = () => {
    const timerId = autoUpdate && setInterval(getStatistics, timer);
    return () => clearInterval(timerId);
  };

  const getStatistics = () => {
    statistics.length || startFetch();
    statService.getStatistics().then(({ statistics, message, success }) => {
      if (success) {
        setStatistics(statistics);
      } else {
        openAlert(message, success);
      }
      finishFetch();
    });
  };

  const intervals = [1, 5, 10, 20, 60, 120];

  return (
    <Paper className={classes.root}>
      <Toolbar>
        <div className={classes.title}>
          <Typography variant="h6" id="tableTitle">
            Statistics
          </Typography>
        </div>
        <div className={classes.spacer} />
        <div className={classes.actions}>
          <FormControlLabel
            label="Auto Update"
            labelPlacement="start"
            control={
              <Switch
                checked={autoUpdate}
                onChange={() => setAutoUpdate(!autoUpdate)}
                color="primary"
              />
            }
          />
          {autoUpdate && (
            <FormControl className={classes.formControl}>
              <Select value={timer} onChange={e => setTimer(e.target.value)}>
                {intervals.map(interval => (
                  <MenuItem value={interval * 1000} key={interval}>
                    {interval}s
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          )}
          <IconButton onClick={() => getStatistics()}>
            <Icon>autorenew</Icon>
          </IconButton>
        </div>
      </Toolbar>
      <div className={classes.tableWrapper}>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Value</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {statistics.map(row => (
              <TableRow key={row.name}>
                <TableCell
                  component="th"
                  scope="row"
                  style={{ textTransform: "capitalize" }}
                >
                  {row.name}
                </TableCell>
                <TableCell>{row.value}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </Paper>
  );
};

export default withStyles(styles)(Statistics);

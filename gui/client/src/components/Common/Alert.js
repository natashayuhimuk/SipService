import React from "react";

import Snackbar from "@material-ui/core/Snackbar";
import SnackbarContent from "@material-ui/core/SnackbarContent";
import IconButton from "@material-ui/core/IconButton";
import green from "@material-ui/core/colors/green";
import Icon from "@material-ui/core/Icon";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({
  close: {
    padding: theme.spacing.unit / 2
  },
  message: {
    display: "flex",
    alignItems: "center"
  },
  icon: {
    fontSize: 20,
    opacity: 0.9,
    marginRight: theme.spacing.unit
  },
  success: {
    backgroundColor: green[600],
    margin: theme.spacing.unit
  },
  error: {
    backgroundColor: theme.palette.error.dark,
    margin: theme.spacing.unit
  }
});

const Alert = ({
  classes,
  message = "",
  success = true,
  open = false,
  closeAlert
}) => {
  const handleClose = (event, reason) => {
    if (reason !== "clickaway") {
      closeAlert();
    }
  };

  return (
    <div>
      <Snackbar
        anchorOrigin={{
          vertical: "top",
          horizontal: "right"
        }}
        open={open}
        autoHideDuration={4000}
        onClose={handleClose}
      >
        <SnackbarContent
          className={classes[success ? "success" : "error"]}
          message={
            <span className={classes.message}>
              <Icon className={classes.icon}>
                {success ? "check_circle_icon" : "error_icon"}
              </Icon>
              {message}
            </span>
          }
          action={[
            <IconButton
              key="close"
              color="inherit"
              className={classes.close}
              onClick={handleClose}
            >
              <Icon>close_icon</Icon>
            </IconButton>
          ]}
        />
      </Snackbar>
    </div>
  );
};

export default withStyles(styles)(Alert);

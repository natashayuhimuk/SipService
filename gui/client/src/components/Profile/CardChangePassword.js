import React, { useState } from "react";

import { withStyles } from "@material-ui/core/styles";
import Card from "@material-ui/core/Card";
import CardActions from "@material-ui/core/CardActions";
import CardContent from "@material-ui/core/CardContent";
import Button from "@material-ui/core/Button";
import CardHeader from "@material-ui/core/CardHeader";
import TextField from "@material-ui/core/TextField";

import { authService } from "../../services";
import { VALID, MESSAGE } from "../../config";

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
  },
  listItem: {
    padding: theme.spacing.unit * 1
  }
});

const ChangePassword = ({ classes, login, setOpen, openAlert }) => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [reNewPassword, setReNewPassword] = useState("");

  const [validCurrentPassword, setValidCurrentPassword] = useState(true);
  const [validNewPassword, setValidNewPassword] = useState(true);

  const onValid = () => {
    const validCurrentPassword = new RegExp(VALID.password).test(
      currentPassword
    );
    const validNewPassword =
      new RegExp(VALID.password).test(newPassword) &&
      newPassword === reNewPassword;
    setValidCurrentPassword(validCurrentPassword);
    setValidNewPassword(validNewPassword);
    return validCurrentPassword && validNewPassword;
  };

  const onSubmit = async () => {
    if (onValid()) {
      const { message, success } = await authService.changePassword(
        currentPassword,
        newPassword
      );
      openAlert(message, success);
      if (success) {
        setOpen(false);
      }
    }
  };

  return (
    <Card>
      <CardHeader title="Change password" className={classes.header} />
      <CardContent className={classes.content}>
        <TextField
          type="password"
          label="Current password"
          value={currentPassword}
          onChange={e => setCurrentPassword(e.target.value)}
          margin="dense"
          error={!validCurrentPassword}
          helperText={!validCurrentPassword && MESSAGE.ERROR.password}
          fullWidth={true}
        />
        <TextField
          type="password"
          label="New password"
          value={newPassword}
          onChange={e => setNewPassword(e.target.value)}
          margin="dense"
          error={!validNewPassword}
          helperText={!validNewPassword && MESSAGE.ERROR.password}
          fullWidth={true}
        />
        <TextField
          type="password"
          label="Re-type new password"
          value={reNewPassword}
          onChange={e => setReNewPassword(e.target.value)}
          margin="dense"
          error={!validNewPassword}
          helperText={!validNewPassword && MESSAGE.ERROR.rePassword}
          fullWidth={true}
        />
      </CardContent>
      <CardActions>
        <div className={classes.spacer} />
        <Button size="small" color="primary" onClick={onSubmit}>
          Ok
        </Button>
        <Button size="small" color="secondary" onClick={() => setOpen(false)}>
          Cancel
        </Button>
      </CardActions>
    </Card>
  );
};

export default withStyles(styles)(ChangePassword);

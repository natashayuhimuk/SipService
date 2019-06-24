import React, { useState } from "react";
import NumberFormat from "react-number-format";

import Button from "@material-ui/core/Button";
import FormControl from "@material-ui/core/FormControl";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import FormHelperText from "@material-ui/core/FormHelperText";

import CircularProgress from "@material-ui/core/CircularProgress";
import withStyles from "@material-ui/core/styles/withStyles";
import { MESSAGE, VALID } from "../../config";

const styles = theme => ({
  paper: {
    padding: "10px",
    margin: "10px",
    display: "flex",
    flexDirection: "column",
    maxWidth: "400px"
  },
  container: {
    position: "fixed",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%"
  },
  wrapper: {
    margin: theme.spacing.unit,
    position: "relative"
  },
  buttonProgress: {
    position: "absolute",
    top: "50%",
    left: "50%",
    marginTop: -12,
    marginLeft: -12
  }
});

const Login = ({ classes, onSignIn }) => {
  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [remember, setRemember] = useState(false);
  const [validNumber, setValidNumber] = useState(true);
  const [validPassword, setValidPassword] = useState(true);
  const [loading, setLoading] = useState(false);

  const onValid = () => {
    const validNumber = new RegExp(VALID.phoneNumber).test(phoneNumber);
    setValidNumber(validNumber);

    const validPassword = new RegExp(VALID.password).test(phoneNumber);
    setValidPassword(validPassword);

    return validNumber && validPassword;
  };

  const onSubmit = async e => {
    e.preventDefault();

    if (onValid()) {
      setLoading(true);
      const success = await onSignIn(
        phoneNumber.replace(/\D+/g, ""),
        password,
        remember
      );
      success || (await setLoading(success));
    }
  };

  return (
    <main className={classes.container}>
      <Paper elevation={2} className={classes.paper}>
        <Typography variant="h5">Sign in</Typography>
        <form onSubmit={onSubmit}>
          <FormControl margin="normal" fullWidth error={!validNumber}>
            <InputLabel>Phone Number</InputLabel>
            <NumberFormat
              type="tel"
              customInput={Input}
              value={phoneNumber}
              onChange={e => setPhoneNumber(e.target.value)}
              onFocus={() => setValidNumber(true)}
              autoFocus
              placeholder="+375 (##) ###-##-##"
              format="+375 (##) ###-##-##"
              mask="_"
            />
            {!validNumber && (
              <FormHelperText id="component-error-text">
                {MESSAGE.ERROR.phoneNumber}
              </FormHelperText>
            )}
          </FormControl>
          <FormControl margin="normal" fullWidth error={!validPassword}>
            <InputLabel>Password</InputLabel>
            <Input
              type="password"
              value={password}
              onChange={e => setPassword(e.target.value)}
              onFocus={() => {
                setValidPassword(true);
              }}
            />
            {!validPassword && (
              <FormHelperText id="component-error-text">
                {MESSAGE.ERROR.password}
              </FormHelperText>
            )}
          </FormControl>
          <FormControlLabel
            control={
              <Checkbox
                checked={remember}
                onChange={e => setRemember(e.target.checked)}
                color="primary"
              />
            }
            label="Remember me"
          />

          <div className={classes.wrapper}>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              disabled={loading}
            >
              Sign in
            </Button>
            {loading && (
              <CircularProgress
                size={24}
                color="primary"
                className={classes.buttonProgress}
              />
            )}
          </div>
        </form>
      </Paper>
    </main>
  );
};

export default withStyles(styles)(Login);

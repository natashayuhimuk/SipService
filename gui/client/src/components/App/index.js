import React from "react";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";

import Main from "../Main";
import { Spinner, Error } from "../Common";
import LoginContainer from "../../containers/LoginContainer";
import AlertContainer from "../../containers/AlertContainer";
import { withStyles } from "@material-ui/core";

const styles = {
  container: {
    display: "flex",
    position: "absolute",
    width: "100%",
    height: "100%",
    justifyContent: "center",
    alignItems: "center"
  }
};

const App = ({ classes, user, loading }) => (
  <Router>
    {loading ? (
      <div className={classes.container}>
        <Spinner />
      </div>
    ) : (
      <Switch>
        <Route
          path="/login"
          render={() => (user ? <Redirect to="/" /> : <LoginContainer />)}
        />
        <Route
          path="/404"
          component={Error}
        />
        <Route
          path="/"
          render={() => (user ? <Main /> : <Redirect to="/login" />)}
        />
      </Switch>
    )}
    <AlertContainer />
  </Router>
);

export default withStyles(styles)(App);

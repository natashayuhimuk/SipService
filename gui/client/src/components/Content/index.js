import React from "react";
import { Route, Redirect, Switch } from "react-router-dom";

import { withStyles } from "@material-ui/core/styles";

import FetchContainer from "../../containers/FetchContainer";
import ProfileContainer from "../../containers/ProfileContainer";
import Statistics from "../../components/Statistics";
import Users from "../../components/Users";
import BlackList from "../../components/BlackList";
import Tariffs from "../../components/Tariffs";

const BlackListContainer = FetchContainer(BlackList);
const TariffsContainer = FetchContainer(Tariffs);
const StatisticsContainer = FetchContainer(Statistics);
const UsersContainer = FetchContainer(Users);

const styles = theme => ({
  content: {
    width: "100%",
    flexGrow: 1,
    padding: theme.spacing.unit * 3
  },
  toolbar: theme.mixins.toolbar
});

const Content = ({ classes, user }) => (
  <main className={classes.content}>
    <div className={classes.toolbar} />
    <Switch>
      <Route exact path="/" component={ProfileContainer} />
      <Route path="/black-list" component={BlackListContainer} />
      <Route
        path="/tariffs"
        render={() =>
          user.role !== "ADMIN" ? <Redirect to="/" /> : <TariffsContainer />
        }
      />
      <Route
        path="/users"
        render={() =>
          user.role !== "ADMIN" ? <Redirect to="/" /> : <UsersContainer />
        }
      />
      <Route
        path="/statistics"
        render={() =>
          user.role !== "ADMIN" ? <Redirect to="/" /> : <StatisticsContainer />
        }
      />
      <Route path="*" render={() => <Redirect to="/404" />} />
    </Switch>
  </main>
);

export default withStyles(styles)(Content);

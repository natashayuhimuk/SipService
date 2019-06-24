import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import CssBaseline from "@material-ui/core/CssBaseline";
import { reloginUser } from "./actions";

import store from "./store";

import AppContainer from "./containers/AppContainer";

reloginUser()(store.dispatch);

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <CssBaseline />
      <AppContainer />
    </BrowserRouter>
  </Provider>,
  document.getElementById("root")
);

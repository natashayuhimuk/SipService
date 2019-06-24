import { createStore } from "redux";

import reducer from "../reducers";

const initialState = {
  loading: {
    authorization: true,
    fetch: false
  },
  alert: {
    open: false,
    message: "",
    success: false
  },
  drawer: false
};

const store = createStore(reducer, initialState);

export default store;

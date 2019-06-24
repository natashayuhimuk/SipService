import { combineReducers } from "redux";

import { default as user } from "./user";
import { default as alert } from "./alert";
import { default as drawer } from "./drawer";
import { default as loading } from "./loading";

const reducer = combineReducers({
  user,
  alert,
  drawer,
  loading
});

export default reducer;

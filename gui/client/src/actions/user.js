import { userService, authService, commonService } from "../services";
import { finishAuthorization, openAlert } from "./";

export const SIGN_IN = "SIGN_IN";
export const SIGN_OUT = "SIGN_OUT";
export const UPDATE_USER = "UPDATE_USER";

export const signIn = (phoneNumber, password, remember) => async dispatch => {
  const { user, token, success, message } = await authService.loginUser({
    phoneNumber,
    password
  });

  openAlert(message, success)(dispatch);
  if (success) {
    if (remember) {
      localStorage.setItem("user", JSON.stringify(token));
    } else {
      sessionStorage.setItem("user", JSON.stringify(token));
    }
    dispatch({
      type: SIGN_IN,
      user
    });
  }
  return await success;
};

export const signOut = () => dispatch => {
  localStorage.removeItem("user");
  sessionStorage.removeItem("user");
  dispatch({
    type: SIGN_OUT
  });
};

export const reloginUser = () => async dispatch => {
  const token = commonService.getToken();
  if (token) {
    const { user, success, message } = await authService.reloginUser();
    if (success) {
      dispatch({
        type: SIGN_IN,
        user
      });
    } else {
      localStorage.removeItem("user");
      sessionStorage.removeItem("user");
      openAlert(message, success)(dispatch);
    }
  }
  finishAuthorization()(dispatch);
};

export const updateUser = newUser => async dispatch => {
  const { user, success, message } = await userService.updateUser(newUser);
  openAlert(message, success)(dispatch);
  if (success) {
    dispatch({
      type: UPDATE_USER,
      user
    });
  }
};

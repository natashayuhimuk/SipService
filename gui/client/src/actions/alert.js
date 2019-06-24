export const OPEN_ALERT = "OPEN_ALERT";
export const CLOSE_ALERT = "CLOSE_ALERT";

export const openAlert = (message, success) => dispatch => {
  dispatch({
    type: OPEN_ALERT,
    message,
    success
  });
};

export const closeAlert = () => dispatch => {
  dispatch({
    type: CLOSE_ALERT
  });
};

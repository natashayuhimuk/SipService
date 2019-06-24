export const CHANGE_DRAWER = "CHANGE_DRAWER";

export const changeDrawer = state => dispatch => {
  dispatch({
    type: CHANGE_DRAWER,
    state
  });
};

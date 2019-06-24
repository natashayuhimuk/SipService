export const START_AUTHORIZATION = "START_AUTHORIZATION";
export const FINISH_AUTHORIZATION = "FINISH_AUTHORIZATION";
export const START_FETCH = "START_FETCH";
export const FINISH_FETCH = "FINISH_FETCH";

export const startAuthorization = () => dispatch => {
  dispatch({
    type: START_AUTHORIZATION
  });
};

export const finishAuthorization = () => dispatch => {
  dispatch({
    type: FINISH_AUTHORIZATION
  });
};

export const strrtFetch = () => dispatch => {
  dispatch({
    type: START_FETCH
  });
};

export const finishFetch = () => dispatch => {
  dispatch({
    type: FINISH_FETCH
  });
};

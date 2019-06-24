import { OPEN_ALERT, CLOSE_ALERT } from "../actions";

export default function reducer(state = {}, action) {
  switch (action.type) {
    case OPEN_ALERT:
      return {
        open: true,
        message: action.message,
        success: action.success
      };

    case CLOSE_ALERT:
      return {
        ...state,
        open: false
      };

    default:
      return state;
  }
}

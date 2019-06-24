import { SIGN_IN, SIGN_OUT, UPDATE_USER } from "../actions";

export default function reducer(state = null, action) {
  switch (action.type) {
    case SIGN_IN:
      return {
        ...action.user
      };

    case SIGN_OUT:
      return null;

    case UPDATE_USER:
      return {
        ...state,
        ...action.user
      };

    default:
      return state;
  }
}

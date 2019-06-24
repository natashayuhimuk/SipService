import {
  START_AUTHORIZATION,
  FINISH_AUTHORIZATION,
  START_FETCH,
  FINISH_FETCH
} from "../actions";

export default function reducer(state = [], action) {
  switch (action.type) {
    case START_AUTHORIZATION:
      return {
        ...state,
        authorization: true
      };

    case FINISH_AUTHORIZATION:
      return {
        ...state,
        authorization: false
      };

    case START_FETCH:
      return {
        ...state,
        fetch: true
      };

    case FINISH_FETCH:
      return {
        ...state,
        fetch: false
      };

    default:
      return state;
  }
}

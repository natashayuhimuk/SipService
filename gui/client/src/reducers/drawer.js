import { CHANGE_DRAWER } from "../actions";

export default function reducer(state = {}, action) {
  switch (action.type) {
    case CHANGE_DRAWER:
      return action.state;

    default:
      return state;
  }
}

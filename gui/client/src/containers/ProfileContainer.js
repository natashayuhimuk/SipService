import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import Profile from "../components/Profile";
import { updateUser, openAlert } from "../actions";

const mapDispatchToProps = dispatch => ({
  updateUser: newUser => updateUser(newUser)(dispatch),
  openAlert: (message, success) => openAlert(message, success)(dispatch)
});

const mapStateToProps = state => ({
  user: state.user
});

const AppBarContainer = withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(Profile)
);

export default AppBarContainer;

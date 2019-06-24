import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import { changeDrawer } from "../actions";
import Drawer from "../components/Drawer";

const mapDispatchToProps = dispatch => ({
  changeDrawer: payload => changeDrawer(payload)(dispatch)
});

const mapStateToProps = state => ({
  role: state.user.role,
  drawer: state.drawer
});

const DrawerContainer = withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(Drawer)
);

export default DrawerContainer;

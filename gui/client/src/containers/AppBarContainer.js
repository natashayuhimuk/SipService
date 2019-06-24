import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import AppBar from "../components/AppBar";
import { changeDrawer, signOut } from "../actions";

const mapDispatchToProps = dispatch => ({
  signOut: () => signOut()(dispatch),
  changeDrawer: payload => changeDrawer(payload)(dispatch)
});

const mapStateToProps = state => ({
  user: state.user,
  loading: state.loading.fetch
});

const AppBarContainer = withRouter(
  connect(
    mapStateToProps,
    mapDispatchToProps
  )(AppBar)
);

export default AppBarContainer;

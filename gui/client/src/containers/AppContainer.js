import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import App from "../components/App";

const mapStateToProps = state => ({
  user: state.user,
  loading: state.loading.authorization
});

const AppContainer = withRouter(connect(mapStateToProps)(App));

export default AppContainer;

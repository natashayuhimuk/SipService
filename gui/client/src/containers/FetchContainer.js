import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

import { finishFetch, openAlert, strrtFetch } from "../actions";

const mapDispatchToProps = dispatch => ({
  openAlert: (message, success) => openAlert(message, success)(dispatch),
  startFetch: () => strrtFetch()(dispatch),
  finishFetch: () => finishFetch()(dispatch)
});

const mapStateToProps = state => ({});

const FetchContainer = Component =>
  withRouter(
    connect(
      mapStateToProps,
      mapDispatchToProps
    )(Component)
  );

export default FetchContainer;

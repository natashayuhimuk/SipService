import React from "react";
import { withRouter } from "react-router-dom";
import { compose } from "redux";

import { withStyles } from "@material-ui/core/styles";
import Drawer from "@material-ui/core/Drawer";
import Hidden from "@material-ui/core/Hidden";

import DrawerList from "./DrawerList";

const drawerWidth = 240;

const styles = theme => ({
  drawer: {
    width: drawerWidth,
    flexShrink: 0
  },
  drawerPaper: {
    width: drawerWidth
  },
  toolbar: theme.mixins.toolbar
});

const ResponsiveDrawer = ({
  classes,
  drawer = false,
  changeDrawer,
  location: { pathname },
  role
}) => (
  <aside>
    <Hidden smDown>
      <Drawer
        className={classes.drawer}
        variant="permanent"
        classes={{
          paper: classes.drawerPaper
        }}
      >
        <div className={classes.toolbar} />
        <DrawerList path={pathname} role={role} changeDrawer={changeDrawer} />
      </Drawer>
    </Hidden>
    <Hidden mdUp>
      <Drawer
        variant="temporary"
        open={drawer}
        onClose={() => changeDrawer(!drawer)}
        classes={{
          paper: classes.drawerPaper
        }}
      >
        <DrawerList path={pathname} role={role} changeDrawer={changeDrawer} />
      </Drawer>
    </Hidden>
  </aside>
);

export default compose(
  withRouter,
  withStyles(styles)
)(ResponsiveDrawer);

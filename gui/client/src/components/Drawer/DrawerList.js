import React, { Fragment } from "react";
import { NavLink } from "react-router-dom";

import Divider from "@material-ui/core/Divider";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";

import Icon from "@material-ui/core/Icon";
import { LINK } from "../../config";

const DrawerList = ({ path, role, changeDrawer }) => (
  <nav>
    <List>
      {LINK.USER.map(link => (
        <ListItem
          button
          component={NavLink}
          key={link.title}
          to={link.to}
          selected={path === link.to}
          onClick={() => {
            changeDrawer(false);
          }}
        >
          <ListItemIcon>
            <Icon>{link.icon}</Icon>
          </ListItemIcon>
          <ListItemText primary={link.title} />
        </ListItem>
      ))}
    </List>
    <Divider />
    {role === "ADMIN" && (
      <Fragment>
        <List>
          {LINK.ADMIN.map(link => (
            <ListItem
              button
              component={NavLink}
              key={link.title}
              to={link.to}
              selected={path === link.to}
              onClick={() => {
                changeDrawer(false);
              }}
            >
              <ListItemIcon>
                <Icon>{link.icon}</Icon>
              </ListItemIcon>
              <ListItemText primary={link.title} />
            </ListItem>
          ))}
        </List>
        <Divider />
      </Fragment>
    )}
  </nav>
);

export default DrawerList;

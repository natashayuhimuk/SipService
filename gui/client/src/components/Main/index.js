import React from "react";

import AppBarContainer from "../../containers/AppBarContainer";
import DrawerContainer from "../../containers/DrawerContainer";
import ContentContainer from "../../containers/ContentContainer";

const Main = () => {
  return (
    <div style={{ display: "flex" }}>
      <AppBarContainer />
      <DrawerContainer />
      <ContentContainer />
    </div>
  );
};

export default Main;

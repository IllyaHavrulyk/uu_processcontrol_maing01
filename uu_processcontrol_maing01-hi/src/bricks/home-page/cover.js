//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent, useEffect, useRef } from "uu5g04-hooks";
import Config from "./config/config";
import Calls from "calls";
import HomePage from "./home-page";
import Home from "../../routes/home";
//@@viewOff:imports

const STATICS = {
  //@@viewOn:statics
  displayName: Config.TAG + "Cover",
  //@@viewOff:statics
};

export const Cover = createComponent({
  ...STATICS,

  //@@viewOn:propTypes
  propTypes: {},
  //@@viewOff:propTypes

  //@@viewOn:defaultProps
  defaultProps: {},
  //@@viewOff:defaultProps

  render(props) {
    //@@viewOn:private

    //@@viewOff:private

    //@@viewOn:interface
    //@@viewOff:interface

    //@@viewOn:render
    return (

      <HomePage />
    )
    //@@viewOff:render
  },
});

export default Cover;

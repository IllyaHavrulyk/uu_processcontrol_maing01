//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent, useEffect, useRef } from "uu5g04-hooks";
import Config from "./config/config";
import Calls from "calls";
import HomePage from "./home-page";
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
    const dataManagerRef = useRef();
    useEffect(() => {
      const intervalKey = setInterval(() => dataManagerRef.current && dataManagerRef.current.reload({}), 30000);
      console.log("Current ---", dataManagerRef.current)
      return () => clearInterval(intervalKey)
    }, [])

    let id = "5ff47fc46ea71a565885ae64";
    let requestData = {
      pageInfo:{
        pageSize : 1,
        pageIndex : 0
      }
    };
    //@@viewOff:private

    //@@viewOn:interface
    //@@viewOff:interface

    //@@viewOn:render
    return (

      <UU5.Common.ListDataManager
        onLoad={Calls.processList}
        onReload={Calls.processList}
        ref_={(ref) => dataManagerRef.current = ref}
        data={requestData}
      >
        {({ viewState, errorState, errorData, data , pageInfo}) => {
          if (errorState) {
            return <Error data={errorData} errorState={errorState}/>;
          } else if (data) {
            return <HomePage
              data={data}
              pageInfo={pageInfo}
            />
          } else {
            return <UU5.Bricks.Loading/>
          }
        }}

      </UU5.Common.ListDataManager>
    )
    //@@viewOff:render
  },
});

export default Cover;

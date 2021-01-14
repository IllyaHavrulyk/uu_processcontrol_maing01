//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent } from "uu5g04-hooks";
import Config from "./config/config";
import MetadataItem from "./metadata-item";
import Uu5Tiles from "uu5tilesg02";
import Calls from "../../calls";
//@@viewOff:imports

const STATICS = {
  //@@viewOn:statics
  displayName: Config.TAG + "MetadataList",
  //@@viewOff:statics
};

export const MetadataList = createComponent({
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
    let requestProcessData = {
      pageInfo:{
        pageSize : 1,
        pageIndex : 0
      }
    }

    let showAllMetadata = (data) =>{
      let processPhases = props.data[0];
      let moderatingEnabled = (processPhases[2].status === "RUNNING");
      return(
       <MetadataItem  data={data} moderatingEnabled={props.moderatingEnabled} intervalKey={props.intervalKey} clearModalInterval={props.clearModalInterval}
                      setModalInterval={props.setModalInterval} moderatingEnabled={moderatingEnabled}/>
      )
    }
    return(
      <UU5.Bricks.Div style="margin-top: 30px;">
        <UuP.Bricks.RouteContent
          level="5"
          descHidden="true"
          header="Files metadata"
          desc="Here you can see metadata which was generated during the parsing of files."
        >
        </UuP.Bricks.RouteContent>
        <Uu5Tiles.Grid data={props.data[1]} >
          {showAllMetadata}
        </Uu5Tiles.Grid>
        <UU5.Common.Div content={Object.keys(props.data[0])}/>
      </UU5.Bricks.Div>


    )
    //@@viewOff:render
  },
});

export default MetadataList;

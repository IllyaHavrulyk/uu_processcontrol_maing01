//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent } from "uu5g04-hooks";
import Config from "./config/config";
import MetadataItem from "./metadata-item";
import Uu5Tiles from "uu5tilesg02";
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


    let showAllMetadata = (data) =>{
      return(
       <MetadataItem  data={data} moderatingEnabled={props.moderatingEnabled} intervalKey={props.intervalKey} clearModalInterval={props.clearModalInterval}
                      setModalInterval={props.setModalInterval}/>
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
        <Uu5Tiles.Grid data={props.data} >
          {showAllMetadata}
        </Uu5Tiles.Grid>
      </UU5.Bricks.Div>

    )
    //@@viewOff:render
  },
});

export default MetadataList;

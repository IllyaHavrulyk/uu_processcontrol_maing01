//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent } from "uu5g04-hooks";
import Config from "./config/config";
import "./style/home-page.less";
import Calls from "../../calls";
import MetadataList from "./metadata-list";
import { useRef, useState, useEffect } from "uu5g04-hooks";
import MetadataItem from "./metadata-item";
import { Uri } from "uu_appg01_core";
import ProcessDetails from "./process-details";

//@@viewOff:imports

const STATICS = {
  //@@viewOn:statics
  displayName: Config.TAG + "HomePage",
  //@@viewOff:statics
};

export const HomePage = createComponent({
  ...STATICS,

  //@@viewOn:propTypes
  propTypes: {},
  //@@viewOff:propTypes

  //@@viewOn:defaultProps
  defaultProps: {},
  //@@viewOff:defaultProps

  render(props) {
    // @@viewOn:private
    const dataManagerProcessRef = useRef();
    useEffect(() => {
      const intervalKey = setInterval(() => dataManagerProcessRef.current && dataManagerProcessRef.current.reload({}), 3000);
      return () => clearInterval(intervalKey)
    }, [])

    let id = "5ff47fc46ea71a565885ae64";
    let requestProcessData = {
      pageInfo:{
        pageSize : 1,
        pageIndex : 0
      }
    };



    const dataManagerRef = useRef();
    const intervalRef = useRef();
    useEffect(() => {
      setModalInterval();
      return clearModalInterval;
    }, [])



    const [modal, setModal] = useState();
    const [byteForZip, setByteForZip] = useState();
    const [alertModerated, setAlertModerated] = useState();

    const requestData = {
      pageInfo: {
        pageSize: 5,
        pageIndex: 0
      }
    }

    function clearModalInterval(){
      clearInterval(intervalRef.current);
    }
    function setModalInterval(){
      intervalRef.current = setInterval(() => dataManagerRef.current && dataManagerRef.current.reload({}), 2000);
    }


    async function makeTwoCalls(){
      let someObject = {};
      someObject.processListResult = await Calls.processList(requestData);
      someObject.metadataListResult = await Calls.metadataGet(requestData);
      let someArray = Array.of(someObject.processListResult.itemList[0].phases, someObject.metadataListResult.itemList);
      return someArray;
    }
    //@@viewOff:private

    //@@viewOn:interface
    //@@viewOff:interface

    //@@viewOn:render
    return (
      <UU5.Bricks.Container>

        <UU5.Common.ListDataManager
          onLoad={Calls.processList}
          onReload={Calls.processList}
          ref_={(ref) => dataManagerProcessRef.current = ref}
          data={requestData}
        >
          {({ viewState, errorState, errorData, data , pageInfo}) => {
            if (errorState) {
              return <Error data={errorData} errorState={errorState}/>;
            } else if (data) {
              return <ProcessDetails
                data={data}
                pageInfo={pageInfo}
              />
            } else {
              return <UU5.Bricks.Loading/>
            }
          }}

        </UU5.Common.ListDataManager>

        <UU5.Common.ListDataManager
          onLoad={makeTwoCalls}
          onReload={makeTwoCalls}
          ref_={(ref) => dataManagerRef.current = ref}
        >
          {(b) => {
            let { viewState, errorState, errorData, data } = b;
            if (errorState) {
              return <Error data={errorData} errorState={errorState} />;
            } else if (data) {
              return <MetadataList
                data={data}
                clearModalInterval={clearModalInterval}
                setModalInterval={setModalInterval}
              />
            } else {
              return "No data found"
            }
          }}

        </UU5.Common.ListDataManager>


      </UU5.Bricks.Container>
    )

    //@@viewOff:render
  },
});

export default HomePage;

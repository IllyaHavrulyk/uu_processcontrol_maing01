//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent } from "uu5g04-hooks";
import Config from "./config/config";
import "./style/home-page.less";
import Calls from "../../calls";
import MetadataList from "./metadata-list";
import { useRef, useState ,  useEffect} from "uu5g04-hooks";
import MetadataItem from "./metadata-item";



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
    //@@viewOn:private
    // const dataManagerRef = useRef();
    // useEffect(() => {
    //   const intervalKey = setInterval(() => dataManagerRef.current && dataManagerRef.current.reload({}), 5000);
    //   console.log("Current ---", dataManagerRef.current)
    //   return () => clearInterval(intervalKey)
    // }, [])

    const [fileUploadModal, setFileUploadModal] = useState();
    const [startProcessAlert, setStartProcessAlert] = useState();
    const [modal,setModal] = useState();
    const formRef = useRef();
    const [alertModerated,setAlertModerated] = useState();
    const processData = props.data[0];
    function openModalUploadFile() {
      fileUploadModal.open({
        header: "Upload File",
        content: <UU5.Forms.Form
          onSave={({ component, values }) => _onSave(component, values)}
          onCancel={() => fileUploadModal.close()}>
          <UU5.Forms.File
            ref_={formRef}
            label="Upload xml file here!"
            placeholder=".xml file"
            size="s"
          />
          <UU5.Forms.Controls/>
        </UU5.Forms.Form>,
        size: "s",

      })
    }

    function _onSave(component, values) {
      console.log("---------------------", formRef);
      let value = formRef.current.getValue();
      let formData = new FormData();
      formData.append("data", value);
      //let call = Calls.createZipBinary(formData);
      fileUploadModal.close();
    };

    function startProcess(requestObj){
      console.log(requestObj);
      Calls.processStart({ id: requestObj })
        .then((data)=>{
          UU5.Environment.
          getRouter().
          setRoute("");
        })
        .catch((error)=>{
          console.log("Failed to start process");
        })
    }

    let receivingRunning = processData.phases[0].status == "RUNNING";
    //@@viewOff:private

    //@@viewOn:interface
    //@@viewOff:interface

    //@@viewOn:render
    return (
      <UU5.Bricks.Container>

        <UuP.Bricks.RouteContent
          level="3"
          descHidden="true"
          header="Home Page"
          desc="On this page you can see current process and also you can start another process , upload files, see the status of each phase"
        >
        </UuP.Bricks.RouteContent>
        <UU5.Bricks.Row>
          <UU5.Bricks.Column colWidth="m-4">
            <UU5.Bricks.AlertBus ref_={item => setStartProcessAlert(item)} position="center"/>
            <UU5.Bricks.Button colorSchema="blue" content="Start Process" size="xl" className="process-btn" onClick={ () => {
              startProcess(props.data[0].id);
              startProcessAlert.addAlert({ content : "Process Started" , colorSchema: "green"});
            }}/>
            <UU5.Bricks.Button colorSchema="success" content="Export" size="xl" className="process-btn"/>
          </UU5.Bricks.Column>

          <UU5.Bricks.Column colWidth="m-6" classname="uu-padding-30" style="margin: 0 0 0 -3vw;">
            <UU5.Bricks.Table >
              <UU5.Bricks.Table.TBody>
                <UU5.Bricks.Table.Tr>
                  <UU5.Bricks.Table.Td content='Receiving' className={"home-page-phase " + processData.phases[0].status.toLowerCase() + "-state" } />
                  <UU5.Bricks.Table.Td content='Validating' className={"home-page-phase " + processData.phases[1].status.toLowerCase() + "-state" }/>
                  <UU5.Bricks.Table.Td content='Moderating' className={"home-page-phase " + processData.phases[2].status.toLowerCase() + "-state" }/>
                </UU5.Bricks.Table.Tr>
              </UU5.Bricks.Table.TBody>
            </UU5.Bricks.Table></UU5.Bricks.Column>
          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Modal ref_={(modal) => setFileUploadModal(modal)} />
            {console.log("Receiving ---", processData.phases[0].status == "RUNNING")}
            <UU5.Bricks.Button colorSchema="blue" content="Upload File" size="xl"  onClick={openModalUploadFile} disabled={!receivingRunning}/>
          </UU5.Bricks.Column>
</UU5.Bricks.Row>
        {/*<UU5.Common.ListDataManager*/}
        {/*  onLoad={Calls.metadataList}*/}
        {/*  onReload={Calls.metadataList}*/}
        {/*  ref_={(ref) => dataManagerRef.current = ref}*/}
        {/*>*/}
        {/*  {({ viewState, errorState, errorData, data }) => {*/}
        {/*    if (errorState) {*/}
        {/*      return <Error data={errorData} errorState={errorState} />;*/}
        {/*    } else if (data) {*/}
        {/*      return <MetadataList*/}
        {/*        data={data}*/}
        {/*        moderatingEnabled={processData.phases.itemList[2].status == "RUNNING"}*/}
        {/*      />*/}
        {/*    } else {*/}
        {/*      return <UU5.Bricks.Loading />*/}
        {/*    }*/}
        {/*  }}*/}

        {/*</UU5.Common.ListDataManager>*/}

      </UU5.Bricks.Container>
    )

    //@@viewOff:render
  },
});

export default HomePage;

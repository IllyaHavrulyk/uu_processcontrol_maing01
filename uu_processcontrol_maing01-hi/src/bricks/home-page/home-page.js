//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent } from "uu5g04-hooks";
import Config from "./config/config";
import "./style/home-page.less";
import Calls from "../../calls";
import MetadataList from "./metadata-list";
import { useRef, useState, useEffect } from "uu5g04-hooks";
import MetadataItem from "./metadata-item";
import MetadataTestComponent from "./metadata-test-component";
import { Uri } from "uu_appg01_core";

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
    const dataManagerRef = useRef();
    const intervalRef = useRef();
    useEffect(() => {
      setModalInterval();
      console.log("Current ---", dataManagerRef.current);
      return clearModalInterval;
    }, [])


    const [fileUploadModal, setFileUploadModal] = useState();
    const [startProcessAlert, setStartProcessAlert] = useState();
    const [modal, setModal] = useState();
    const [byteForZip, setByteForZip] = useState();
    const formRef = useRef();
    const [alertModerated, setAlertModerated] = useState();
    const processData = props.data[0];
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
        formData.append("document", value);
        let call = Calls.metadataUploadFile(formData);
        fileUploadModal.close();
      };

    function startProcess(requestObj) {
      console.log(requestObj);
      Calls.processStart({ id: requestObj })
        .then((data) => {
          UU5.Environment.getRouter().setRoute("");
        })
        .catch((error) => {
          console.log("Failed to start process");
        })
    }


    function getExportBytes() {
      return <UU5.Bricks.Link
        download={true}
        href={getUriForExport()}
      >
        <UU5.Bricks.Button colorSchema="success" content="Export" size="xl" className="process-btn"/>
      </UU5.Bricks.Link>
    }

    function getUriForExport() {
      let uri = Uri.UriBuilder.parse(window.location.href);
      if (uri.asid !== undefined) {
        return "http://localhost:8083/uu-datamanagement-maing01/" + uri.asid + "-" + uri.awid + "/export";
      }
      return "http://localhost:8083/uu-datamanagement-maing01/" + uri.awid + "/export";
    }

    let receivingRunning = processData.phases[0].status === "RUNNING";
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
            <UU5.Bricks.Button colorSchema="blue" content="Start Process" size="xl" className="process-btn" onClick={() => {
              startProcess(props.data[0].id);
              startProcessAlert.addAlert({ content: "Process Started", colorSchema: "green" });
            }}/>
            <UU5.Bricks.Button colorSchema="success" content="Export" onClick={getExportBytes} size="xl" className="process-btn"/>
          </UU5.Bricks.Column>

          <UU5.Bricks.Column colWidth="m-6" classname="uu-padding-30" style="margin: 0 0 0 -3vw;">
            <UU5.Bricks.Table>
              <UU5.Bricks.Table.TBody>
                <UU5.Bricks.Table.Tr>
                  <UU5.Bricks.Table.Td content='Receiving' className={"home-page-phase " + processData.phases[0].status.toLowerCase() + "-state"}/>
                  <UU5.Bricks.Table.Td content='Validating' className={"home-page-phase " + processData.phases[1].status.toLowerCase() + "-state"}/>
                  <UU5.Bricks.Table.Td content='Moderating' className={"home-page-phase " + processData.phases[2].status.toLowerCase() + "-state"}/>
                </UU5.Bricks.Table.Tr>
              </UU5.Bricks.Table.TBody>
            </UU5.Bricks.Table></UU5.Bricks.Column>
          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Modal ref_={(modal) => setFileUploadModal(modal)}/>
            {console.log("Receiving ---", processData.phases[0].status == "RUNNING")}
            <UU5.Bricks.Button colorSchema="blue" content="Upload File" size="xl" onClick={openModalUploadFile} disabled={!receivingRunning}/>
          </UU5.Bricks.Column>
        </UU5.Bricks.Row>

        <UU5.Common.ListDataManager
          onLoad={Calls.metadataGet}
          onReload={Calls.metadataGet}
          ref_={(ref) => dataManagerRef.current = ref}
        >
          {(b) => {
            let { viewState, errorState, errorData, data } = b;
            if (errorState) {
              return <Error data={errorData} errorState={errorState} />;
            } else if (data) {
              return <MetadataList
                data={data}
                moderatingEnabled={processData.phases[2].status == "RUNNING"}
                intervalKey={null}
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

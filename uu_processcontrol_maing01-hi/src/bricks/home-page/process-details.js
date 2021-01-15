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
//@@viewOff:imports

const STATICS = {
  //@@viewOn:statics
  displayName: Config.TAG + "ProcessDetails",
  //@@viewOff:statics
};

export const ProcessDetails = createComponent({
  ...STATICS,

  //@@viewOn:propTypes
  propTypes: {},
  //@@viewOff:propTypes

  //@@viewOn:defaultProps
  defaultProps: {},
  //@@viewOff:defaultProps

  render(props) {
    //@@viewOn:private

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

    const processData = props.data[0];
    let receivingRunning = processData.phases[0].status === "RUNNING";
    let validationRunning = processData.phases[1].status === "RUNNING";
    let moderatingRunning = processData.phases[2].status === "RUNNING";

    function isProcessEnded(){
      if(processData.phases[0].status === "OK" && processData.phases[1].status === "OK" && processData.phases[2].status === "OK"){
        return true;
      }else if(processData.phases[0].status === "NOTOK" || processData.phases[1].status === "NOTOK" || processData.phases[2].status === "NOTOK"){
        return false;
      }
      return false;
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
      Calls.datamanagementClean();
      Calls.processStart({ id: requestObj })
        .then((data) => {
          UU5.Environment.getRouter().setRoute("");
        })
        .catch((error) => {
          console.log("Failed to start process");
        })
    }

    const [fileUploadModal, setFileUploadModal] = useState();
    const [startProcessAlert, setStartProcessAlert] = useState();
    const formRef = useRef();

    function getExportBytes() {
      return <UU5.Bricks.Link
        download={true}
        href={getUriForExport()}
        disabled={!(isProcessEnded())}
      >
        <UU5.Bricks.Button colorSchema={isProcessEnded() ? "success" : "danger"} content="Export" size="xl" className="process-btn" disabled={!(isProcessEnded())}/>
      </UU5.Bricks.Link>
    }

    function getUriForExport() {
      let uri = Uri.UriBuilder.parse(window.location.href);
      if (uri.asid !== undefined) {
        return "http://localhost:8083/uu-datamanagement-maing01/" + uri.asid + "-" + uri.awid + "/export";
      }
      return "http://localhost:8083/uu-datamanagement-maing01/" + uri.awid + "/export";
    }
    //@@viewOff:private

    //@@viewOn:interface
    //@@viewOff:interface

    //@@viewOn:render
    Calls.processValidate( {id : props.data[0].id});
    return(
      <div>
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
            {getExportBytes}
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
            <UU5.Bricks.Button colorSchema="blue" content="Upload File" size="xl" onClick={openModalUploadFile} disabled={!receivingRunning}/>
          </UU5.Bricks.Column>
        </UU5.Bricks.Row>
      </div>
    )
    //@@viewOff:render
  },
});

export default ProcessDetails;

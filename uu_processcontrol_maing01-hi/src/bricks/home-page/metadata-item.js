//@@viewOn:imports
import UU5 from "uu5g04";
import { createComponent } from "uu5g04-hooks";
import Config from "./config/config";
import { useRef, useState } from "uu5g04-hooks";
//@@viewOff:imports

const STATICS = {
  //@@viewOn:statics
  displayName: Config.TAG + "MetadataItem",
  //@@viewOff:statics
};

export const MetadataItem = createComponent({
  ...STATICS,

  //@@viewOn:propTypes
  propTypes: {},
  //@@viewOff:propTypes

  //@@viewOn:defaultProps
  defaultProps: {},
  //@@viewOff:defaultProps

  render(props) {
    //@@viewOn:private
    const formRef = useRef();
    const [moderateModal, setModerateModal] = useState();
    const [alertModerated,setAlertModerated] = useState();
    function openModerateMetadata() {
      moderateModal.open({
        header: "Moderate Metadata",
        content: <UU5.Forms.Form
          onSave={({ component, values }) => _onModerateSave(component, values)}
          onCancel={() => moderateModal.close()}>
          <UU5.Forms.Text
            required
            name="receiver"
            label="Metadata receiver"
            placeholder="Enter receiver.."
            value={props.data.data.receiver || ""}/>
          <UU5.Forms.Text
            required
            name="sender"
            label="Metadata sender"
            placeholder="Enter sender.."
            value={props.data.data.sender || ""}/>
          <UU5.Forms.Text
            required
            name="domain"
            label="Metadata domain"
            placeholder="Enter domain.."
            value={props.data.data.domain || ""}/>

          <UU5.Forms.Controls/>
        </UU5.Forms.Form>,
        size: "s",

      })
    }


    function _onModerateSave(component, values) {
      // Calls.createTopic(requestObj)
      //   .then((data)=>{
      //     UU5.Environment.
      //     getRouter().
      //     setRoute("list");
      //   })
      //   .catch((error)=>{
      //     console.log("ooops...");
      //   })
      alertModerated.addAlert({ content : "Metadata Moderated" , colorSchema: "green"});
      console.log("---------------------", formRef);
      moderateModal.close();
      // let value = formRef.current.getValue();
      // let formData = new FormData();
      // formData.append("data", value);
      // //let call = Calls.createZipBinary(formData);

    };

    function showValidIcon(data){
      if(data == "true"){
        return("mdi-check");
      }else if(data == "false"){
        return("mdi-backspace");
      }else{
        return("uu5-clock");
      }
    }

    function showValidCssStyle(data){
      if(data == "true"){
        return "file-icon-valid";
      }else if(data == "false"){
        return "file-icon-invalid";
      }else{
        return "file-icon-checking";
      }
    }

    function showFileValidity(data){
      if(data == "true"){
        return "file-valid";
      }else if(data == "false"){
        return "file-invalid";
      }else{
        return "file-checking";
      }
    }
    //@@viewOff:private

    //@@viewOn:interface
    let isValid = props.data.data.valid;
    //@@viewOff:interface

    //@@viewOn:render
    return(
      <UU5.Bricks.Div className={"metadata-item " + showFileValidity(isValid)}>
        <UU5.Bricks.Row>
          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Header level={5} content={props.data.data.filename} style="margin: 0;" className="metadata-filename"/>
          </UU5.Bricks.Column>
          <UU5.Bricks.Column colWidth="m-1">
            <UU5.Bricks.Icon icon={showValidIcon(isValid)} className={"home-page-icon " + showValidCssStyle(isValid)} />
          </UU5.Bricks.Column>
          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Text content={props.data.data.receiver} className="metadata-filename"/>
          </UU5.Bricks.Column>
          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Text content={props.data.data.sender} className="metadata-filename"/>
          </UU5.Bricks.Column>

          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Text content={props.data.data.metadata} className="metadata-filename"/>
          </UU5.Bricks.Column>

          <UU5.Bricks.Column colWidth="m-2">
            <UU5.Bricks.Modal ref_={(modal) => setModerateModal(modal)} />
            <UU5.Bricks.AlertBus ref_={item => setAlertModerated(item)} position="center"/>
            {console.log(props.moderatingEnabled)}
            <UU5.Bricks.Button colorSchema="success" content="Edit" size="xl" className="process-btn" onClick={openModerateMetadata} disabled={!props.moderatingEnabled}/>
          </UU5.Bricks.Column>
        </UU5.Bricks.Row>
      </UU5.Bricks.Div>
    )
    //@@viewOff:render
  },
});

export default MetadataItem;

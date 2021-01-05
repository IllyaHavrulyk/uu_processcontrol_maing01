import UU5 from "uu5g04";
import UuProcesscontrol from "uu_processcontrol_maing01-hi";

const { shallow } = UU5.Test.Tools;

describe(`UuProcesscontrol.Bricks.HomePage.HomePage`, () => {
  it(`default props`, () => {
    const wrapper = shallow(<UuProcesscontrol.Bricks.HomePage.HomePage />);
    expect(wrapper).toMatchSnapshot();
  });
});

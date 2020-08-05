import React from 'react';

import { Input, Button, Table, Modal, Popconfirm, Form, Switch, message, Spin, Tag } from 'antd';

import { PlusOutlined} from '@ant-design/icons';

import { listPageable, insert, update, remove } from '@/services/${servicePath}';

interface Values {
	<#list fields as field>
      <#if (field['formType'])??>
        ${field['columnHumpName']}: ${field['javaDataType']};
      </#if>
	</#list>
}

interface CollectionCreateFormProps {
  title: string;
  visible: boolean;
  formRef: any;
  onCreate: (values: Values) => void;
  onCancel: () => void;
}

const ${entityName}Form: React.FC<CollectionCreateFormProps> = ({
  title,
  visible,
  formRef,
  onCreate,
  onCancel,
}) => {
  const [form] = Form.useForm();
  const { TextArea  } = Input;
  const formItemLayout = {
    labelCol: {
      xs: { span: 16 },
      sm: { span: 6 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 16 },
    },
  };

  return (
    <Modal
        forceRender
        title={title}
        visible={visible}
        onOk={() => {
          form
            .validateFields()
            .then((values: any) => {
              onCreate(values);
              form.resetFields();
            })
        }}
        onCancel={() => {
          form.resetFields();
          onCancel();
        }}
      >
          <Form {...formItemLayout} form={form} ref={formRef}>
            <Form.Item name="id">
                <Input style={{ display: 'none' }} />
            </Form.Item>
			<#list fields as field>
			<#if field['columnHumpName'] != 'id'>
                <Form.Item label="${field['columnComment']}" name="${field['columnHumpName']}"
				<#if !field['nullable']>
					rules={ [{ required: true, message: '${field['columnComment']}不能为空!' }] }
				</#if>
                  >
					<Input placeholder="请输入${field['columnComment']}" />
				</Form.Item>
            </#if>
			</#list>
          </Form>
        </Modal>
  );
};

class ${entityName}Page extends React.Component {
  state = {
    title: '',
    tableData: [],
    isSpinning: false,
    visible: false
  };

  formRef: any = React.createRef();

  componentDidMount() {
    this.listPageable();
  }

   // 点击新增按钮
   showModal = () => {
    this.setState({ visible: true, title: '新增' });
  }

  // 点击编辑按钮
  edit${entityName} = (record: any) => {
    this.formRef.current.setFieldsValue(record);
    this.setState({ visible: true, title: '编辑' });
  }

  handleCancel = () => {
    this.setState({ visible: false });
  };

  handleCreate = (values: any) => {
    const $ = this;
    const {title} = this.state;
      if (title === '新增') {
        insert(values)
          .then(res => {
            if (res && res.response && res.response.status === 201) {
              message.success('添加成功');
              $.setState({ visible: false });
              $.listPageable();
            }
          })
      } else {
        update(values)
          .then(res => {
            if (res && res.response && res.response.status === 204) {
              message.success('编辑');
              $.setState({ visible: false });
              $.listPageable();
            }
          })
      }
  };

   // 确认删除
   handleDeleteConfirm = (record: { [x: string]: any; }) => {
    const $ = this;
    remove(record.id)
      .then(res => {
        console.log('delete', res);
        if (res && res.response && res.response.status === 200) {
          message.success('删除成功');
          $.setState({ page: 0 });
          $.listPageable();
        }
      })
  }

  listPageable() {
    this.setState({ isSpinning: true });
    const $ = this;
    listPageable()
      .then((res: any) => {
        $.setState({ tableData: res, isSpinning: false });
      })
      .catch((error: any) => {
        console.log(error);
        $.setState({ isSpinning: false });
      });
  }

  render() {

    const columns = [
	<#list fields as field>
	  {
        title: "${field['columnComment']}",
        dataIndex: "${field['columnHumpName']}",
        key: "${field['columnHumpName']}",
	  },
	</#list>
      {
        title: '操作',
        key: 'action',
        width: '12%',
        render: (text: any, record: { [x: string]: any; id: any }) => (
          <span>
            <Button type="link" size="small" onClick={() => this.edit${entityName}(record)}>编辑</Button>
            <Popconfirm
              title="确认删除?"
              onConfirm={() => this.handleDeleteConfirm(record)}
              okText="确认"
              cancelText="取消"
            >
              <Button type="link" size="small">删除</Button>
            </Popconfirm>
          </span>
        ),
      }
    ]

  return(
      <div>
        <Button type="primary" icon={<PlusOutlined/>} onClick={this.showModal}>新增</Button>
        <Spin spinning={this.state.isSpinning}>
          <Table style={{ marginBottom: 20 }} columns={columns} pagination={false} rowKey="id"
                 dataSource={this.state.tableData}/>
        </Spin>
        <${entityName}Form
                formRef={this.formRef}
                title={this.state.title}
                visible={this.state.visible}
                onCancel={this.handleCancel}
                onCreate={this.handleCreate}
        />
      </div>
  )
  }
}

export default ${entityName}Page;

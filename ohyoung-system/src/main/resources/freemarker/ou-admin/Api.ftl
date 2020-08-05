import request from '@/utils/request';


// 查询所有${entityCnName}
export async function listPageable() {
    return request.get('/server${requestUrl}/${entityName?uncap_first}s');
}

// 新增${entityCnName}
export async function insert(param: any) {
    return request.post('/server${requestUrl}/${entityName?uncap_first}s', {
        data: param,
        getResponse: true
    });
}

// 编辑${entityCnName}
export async function update(param: any) {
    return request.put('/server${requestUrl}/${entityName?uncap_first}s', {
        data: param,
        getResponse: true
    });
}

// 删除${entityCnName}
export async function remove(id: any) {
    return request.delete(`/server${requestUrl}/${entityName?uncap_first}s/${r'${id}'}`, {
        getResponse: true
    });
}

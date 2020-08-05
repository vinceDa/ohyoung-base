package ${package}.repository;

import ${package}.domain.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author ${author}
* @date ${date}
*/
public interface ${entityName}Repository extends JpaRepository<${entityName}, Long>, JpaSpecificationExecutor {


}

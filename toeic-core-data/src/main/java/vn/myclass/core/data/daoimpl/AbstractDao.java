package vn.myclass.core.data.daoimpl;

import org.hibernate.*;
import vn.myclass.core.commom.constant.CoreConstant;
import vn.myclass.core.commom.utils.HibernateUtil;
import vn.myclass.core.data.dao.GenericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractDao<ID extends Serializable,T> implements GenericDao<ID,T> {
    // class định nghĩa các phương thức thực hiện của session(truy vấn) chung
    // T là class
    // ID la khóa chính
    // thuộc tính để truyền các bản cần vào
    private Class<T> persistenceClass;
    public AbstractDao()
    {
        this.persistenceClass =(Class<T>) (((ParameterizedType) getClass().getGenericSuperclass())).getActualTypeArguments()[1];
        // dựa vào t để lấy tên bảng theo enity, 1 ở dây là vị trí thứ 1 là T trong generic
    }


    public String getPersistenceClassName()
    {
        return this.persistenceClass.getSimpleName();
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<T>();
        // khởi tạo giao diện (transaction)
        Transaction transaction = null;
        // tạo đối tượng session
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // bắt đầu một phiên giao dịch (session) thong qua transaction
            transaction = session.beginTransaction();
            //HQL
            StringBuilder sql = new StringBuilder("from ");
            sql.append(this.getPersistenceClassName());
            Query query = session.createQuery(sql.toString());
            list = query.list();
            //
            transaction.commit();
        }catch (HibernateException e)
        {
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return list;
    }
    //hàm up date
    public T update(T entity) {
        T result = null;
        Transaction transaction = null;
        // tạo đối tượng session
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            // sử dụng phương thức merge của session de update du lieu
            Object object = session.merge(entity);
            result = (T) object;
            //
            transaction.commit();
        }catch(HibernateException e)
        {
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return result;
    }

    public void save(T entity) {
        // phương thức thêm kèm lưu lại vào dữ liệu
        Transaction transaction = null;
        // tạo đối tượng session
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            // phương thức persist dùng để lưu dữ liệu vào database
            session.persist(entity);
            //
            transaction.commit();
        }catch(HibernateException e)
        {
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public T findById(ID var1) {
        // tìm đối tượng theo id
        T result = null;
        Transaction transaction = null;
        // tạo đối tượng session
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();

            //phuong thức get trả về đối tượng theo id truyền vào
            result = (T) session.get(persistenceClass, var1);
            if (result == null)
            {
                throw new ObjectNotFoundException("NOt FOUND "+var1,null);
            }
            //
            transaction.commit();
        }catch(HibernateException e)
        {
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return result;
    }

    public Object[] findByProperty(Map<String,Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<T> list = new ArrayList<T>();
        Transaction transaction = null;
        Object totalitem = 0;
        // tạo đối tượng session
        Session session = HibernateUtil.getSessionFactory().openSession();
        String[] params = new String[property.size()];
        Object[] value = new Object[property.size()];
        int i =0;
        for(Map.Entry<String,Object> item : property.entrySet() )
        {
            params[i] = item.getKey();
            value[i] = item.getValue();
            i++;
        }
        try {
            transaction = session.beginTransaction();
            //
            // tìm danh sach
            StringBuilder sql = new StringBuilder("from ");
            sql.append(getPersistenceClassName());
            if(property.size()>0)
            {
                for(int i1=0;i1<params.length;i1++)
                {
                    if(i1==0)
                    {
                        sql.append(" where ").append(params[i1]).append(" = : ").append(params[i1]);
                    }
                    else
                    {
                        sql.append(" and ").append(params[i1]).append(" = : ").append(params[i1]);
                    }
                }
            }
            if(sortExpression != null && sortDirection != null)
            {
                sql.append(" order by ");
                sql.append(sortExpression);
                sql.append(" "+(sortDirection.equals(CoreConstant.SORT_ASC) ? "asc" : "desc"));
            }
            Query queryProperTy1 = session.createQuery(sql.toString());
            if(property.size()>0)
            {
                for(int i2=0;i2<params.length;i2++)
                {
                    queryProperTy1.setParameter(params[i2],value[i2]);
                }
            }
            if(offset != null && offset >= 0 )
            {
                queryProperTy1.setFirstResult(offset);
            }
            if(limit != null && limit >0)
            {
                queryProperTy1.setMaxResults(limit);
            }
            list = queryProperTy1.list();
            // tìm tổng số phần tử
            StringBuilder sql2 = new StringBuilder("select count(*) from ");
            sql2.append(getPersistenceClassName());
            if(property.size()>0)
            {
                for(int k=0;k<params.length;k++)
                {
                    if(k==0)
                    {
                        sql.append(" where ").append(params[k]).append(" = : ").append(params[k]);
                    }
                    else
                    {
                        sql.append(" and ").append(params[k]).append(" = : ").append(params[k]);
                    }
                }
            }
            Query queryProperty2 = session.createQuery(sql2.toString());
            if(property.size()>0)
            {
                for(int k2=0;k2<params.length;k2++)
                {
                    queryProperty2.setParameter(params[k2],value[k2]);
                }
            }
            // trả về tổng sổ phần tử
            totalitem = queryProperty2.list().get(0);
            //
            transaction.commit();
        }catch(HibernateException e)
        {
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return new Object[]{totalitem,list};
    }

    public Integer delete(List<ID> ids) {
        // biến count dùng để số lần xóa các phần tử , nếu count bằng với số phần tử cần xóa thì thành công
        Integer count = 0;
        Transaction transaction = null;
        // tạo đối tượng session
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            //
            // duyệt danh sách ids
            for(ID item : ids)
            {
                T t = (T) session.get(persistenceClass,item);
                session.delete(t);
                count++;
            }
            //
            transaction.commit();
        }catch(HibernateException e)
        {
            transaction.rollback();
            throw e;
        }finally {
            session.close();
        }
        return count;
    }
}

/*
Copyright or © or Copr. Université de Tours
contributor(s) : Geoffroy VIBRAC (2012/01/01)

geoffroy.vibrac@gmail.com

This software is a computer program whose purpose is to [describe
functionalities and technical features of your software].

This software is governed by the CeCILL license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL license and that you accept its terms.
*/

package net.vibrac.quinzaine.db.Manager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
 

  private static final PersistenceManager singleton = new PersistenceManager();
  
  protected EntityManagerFactory emf;
  private static String entityName = "quinzaine";
  
  public static PersistenceManager getInstance() {
    
    return singleton;
  }
  
  private PersistenceManager() {
  }
 
  public EntityManagerFactory getEntityManagerFactory() {
    
    if (emf == null)
      createEntityManagerFactory();
    return emf;
  }
  
  public void closeEntityManagerFactory() {
    
    if (emf != null) {
      emf.close();
      emf = null;
    }
  }
  
  protected void createEntityManagerFactory() {
        try{
            this.emf = Persistence.createEntityManagerFactory(entityName);
        }catch(Exception ex){
             System.out.println(ex.getMessage());
        }
  }

    public static void setEntityName(String entityNameLocal) {
        entityName = entityNameLocal;
    }

    public static String getEntityName() {
        return entityName;
    }
}
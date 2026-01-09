export interface Expense {
  id: string;              
  userId: string;          
  date: Date;              
  category: string;        
  description?: string;    
  amount: number;          
  createdAt: Date;         
}
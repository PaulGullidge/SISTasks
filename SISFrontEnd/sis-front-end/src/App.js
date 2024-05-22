import React, { useState } from 'react';
import ProductList from './mycomponents/ProductList';
import ProductForm from './mycomponents/ProductForm';
import ProductFilter from './mycomponents/ProductFilter';

const App = () => {
  // create the application data using useState hook
  const [products, setProducts] = useState([]);
  const [filter, setFilter] = useState('');

  const addProduct = (product) => {
    setProducts([...products, product]);
  };

  const updateProduct = (updatedProduct) => {
    setProducts(products.map(product => 
      product.id === updatedProduct.id ? updatedProduct : product
    ));
  };

  const deleteProduct = (id) => {
        setProducts(products.filter(product => product.id !== id));
  };

  return (
    <div className="app">
      <h1>Paul's Supermarket Products</h1>
        <ProductForm addProduct={addProduct} />
        <ProductFilter setFilter={setFilter} />
        <div className='product-item'>
        <table align='center'>
          <tr>
            <th>Product Name</th>
            <th>Description</th>
            <th>Expiry Date</th>
            <th>Category</th>
            <th>Price</th>
            <th></th>
          </tr>
        <ProductList
          products={products}
          filter={filter}
          updateProduct={updateProduct}
          deleteProduct={deleteProduct}
        />
        </table>
        </div>
    </div>
  );
};

export default App;

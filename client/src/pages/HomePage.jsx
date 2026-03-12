import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import api from '../api/axios';
import { BookCard } from '../components/BookCard';
import { BookForm } from '../components/BookForm';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';
import { PlusIcon, SearchIcon, AlertCircleIcon } from 'lucide-react';
import { toast } from 'react-toastify';

export function HomePage() {
  const [books, setBooks] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    setIsLoading(true);
    try {
      const response = await api.get('/books');
      setBooks(response.data);
      setError(null);
    } catch (err) {
      console.error("Failed to fetch books", err);
      setError("Failed to load books. Please try again.");
    } finally {
      setIsLoading(false);
    }
  };

  const handleCreateBook = async (data) => {
    setIsSubmitting(true);
    try {
      const response = await api.post('/books', data);
      setBooks([...books, response.data]);
      setIsModalOpen(false);
      toast.success('Book created successfully!');
    } catch (err) {
      console.error(err);
      let message = err.response?.data?.message || 'Failed to create book';
      if (err.response?.data?.errors && Array.isArray(err.response.data.errors)) {
          message = err.response.data.errors.map(e => e.defaultMessage).join(', ');
      }
      toast.error(message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleUpdateBook = async (data) => {
    if (!editingBook) return;
    setIsSubmitting(true);
    try {
      const response = await api.put(`/books/${editingBook.id}`, data);
      setBooks(books.map((b) => (b.id === editingBook.id ? response.data : b)));
      setEditingBook(null);
      setIsModalOpen(false);
      toast.success('Book updated successfully!');
    } catch (err) {
      console.error(err);
      let message = err.response?.data?.message || 'Failed to update book';
       if (err.response?.data?.errors && Array.isArray(err.response.data.errors)) {
          message = err.response.data.errors.map(e => e.defaultMessage).join(', ');
      }
      toast.error(message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleDeleteBook = async (id) => {
    if (window.confirm('Are you sure you want to delete this book?')) {
      try {
        await api.delete(`/books/${id}`);
        setBooks(books.filter((b) => b.id !== id));
        toast.success('Book deleted successfully');
      } catch (err) {
        console.error(err);
        toast.error('Failed to delete book');
      }
    }
  };

  const openCreateModal = () => {
    setEditingBook(null);
    setIsModalOpen(true);
  };

  const openEditModal = (book) => {
    setEditingBook(book);
    setIsModalOpen(true);
  };

  const filteredBooks = books.filter(book => 
    book.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
    book.author.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div className="container mx-auto p-4 md:p-8 space-y-8">
      <div className="flex flex-col md:flex-row justify-between items-center gap-4">
        <div>
          <h1 className="text-4xl font-extrabold tracking-tight lg:text-5xl text-primary">Library Manager</h1>
          <p className="text-muted-foreground mt-2">Manage your book collection with ease.</p>
        </div>
        <Button onClick={openCreateModal} className="w-full md:w-auto">
          <PlusIcon className="mr-2 h-4 w-4" /> Add New Book
        </Button>
      </div>

      <div className="relative">
        <SearchIcon className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
        <Input 
          placeholder="Search books by title or author..." 
          className="pl-10"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
      </div>

      {isLoading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {[...Array(6)].map((_, i) => (
            <div key={i} className="h-64 rounded-lg border bg-card text-card-foreground shadow-sm animate-pulse bg-gray-100 dark:bg-gray-800" />
          ))}
        </div>
      ) : error ? (
        <div className="flex flex-col items-center justify-center p-8 text-center space-y-4">
          <AlertCircleIcon className="h-12 w-12 text-destructive" />
          <h3 className="text-lg font-semibold">{error}</h3>
          <Button variant="outline" onClick={fetchBooks}>Retry</Button>
        </div>
      ) : filteredBooks.length === 0 ? (
        <div className="text-center py-12">
          <h3 className="text-lg font-semibold text-muted-foreground">No books found.</h3>
          <p className="text-sm text-muted-foreground">Try adjusting your search or add a new book.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredBooks.map((book) => (
            <BookCard 
              key={book.id} 
              book={book} 
              onEdit={openEditModal} 
              onDelete={handleDeleteBook} 
            />
          ))}
        </div>
      )}

      {isModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm p-4">
          <div className="w-full max-w-lg bg-background rounded-lg shadow-lg relative max-h-[90vh] overflow-y-auto">
            <BookForm 
              initialData={editingBook || {}} 
              onSubmit={editingBook ? handleUpdateBook : handleCreateBook} 
              onCancel={() => setIsModalOpen(false)} 
              isLoading={isSubmitting}
              isEditing={!!editingBook}
            />
          </div>
        </div>
      )}
    </div>
  );
}

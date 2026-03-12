import React from 'react';
import { useForm } from 'react-hook-form';
import { Button } from './ui/Button';
import { Input } from './ui/Input';
import { Card, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from './ui/Card';

export function BookForm({ initialData = {}, onSubmit, onCancel, isLoading, isEditing }) {
  const { register, handleSubmit, formState: { errors } } = useForm({
    defaultValues: {
      title: initialData.title || '',
      author: initialData.author || '',
      isbn: initialData.isbn || '',
      publicationYear: initialData.publicationYear || new Date().getFullYear(),
      publisher: initialData.publisher || '',
      price: initialData.price || 0.0,
      totalPages: initialData.totalPages || 0,
      description: initialData.description || '',
    },
  });

  return (
    <Card className="w-full max-w-lg mx-auto">
      <CardHeader>
        <CardTitle>{isEditing ? 'Edit Book' : 'Add New Book'}</CardTitle>
        <CardDescription>Enter the book details below.</CardDescription>
      </CardHeader>
      <form onSubmit={handleSubmit(onSubmit)}>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <label className="text-sm font-medium">Title</label>
            <Input {...register('title', { required: 'Title is required', minLength: { value: 2, message: 'Min length 2' } })} placeholder="Clean Code" />
            {errors.title && <p className="text-sm text-destructive">{errors.title.message}</p>}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <label className="text-sm font-medium">Author</label>
              <Input {...register('author', { required: 'Author is required' })} placeholder="Robert C. Martin" />
              {errors.author && <p className="text-sm text-destructive">{errors.author.message}</p>}
            </div>
            <div className="space-y-2">
              <label className="text-sm font-medium">ISBN</label>
              <Input {...register('isbn', { required: 'ISBN is required' })} placeholder="978-0132350884" />
              {errors.isbn && <p className="text-sm text-destructive">{errors.isbn.message}</p>}
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
             <div className="space-y-2">
              <label className="text-sm font-medium">Year</label>
              <Input type="number" {...register('publicationYear', { valueAsNumber: true, min: 1000, max: 2026 })} />
            </div>
            <div className="space-y-2">
              <label className="text-sm font-medium">Publisher</label>
              <Input {...register('publisher')} placeholder="Prentice Hall" />
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <label className="text-sm font-medium">Price ($)</label>
              <Input type="number" step="0.01" {...register('price', { valueAsNumber: true, required: 'Price is required', min: 0 })} />
              {errors.price && <p className="text-sm text-destructive">{errors.price.message}</p>}
            </div>
             <div className="space-y-2">
              <label className="text-sm font-medium">Pages</label>
              <Input type="number" {...register('totalPages', { valueAsNumber: true, min: 1 })} />
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-medium">Description</label>
            <textarea
              className="flex min-h-[80px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
              {...register('description')}
              placeholder="A brief summary..."
            />
          </div>
        </CardContent>
        <CardFooter className="flex justify-between">
          <Button type="button" variant="outline" onClick={onCancel}>Cancel</Button>
          <Button type="submit" isLoading={isLoading}>{isEditing ? 'Update Book' : 'Create Book'}</Button>
        </CardFooter>
      </form>
    </Card>
  );
}

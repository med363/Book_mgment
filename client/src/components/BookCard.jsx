import React from 'react';
import { Card, CardContent, CardFooter, CardHeader, CardTitle, CardDescription } from './ui/Card';
import { Button } from './ui/Button';
import { PencilIcon, Trash2Icon } from 'lucide-react';
import { format } from 'date-fns';

export function BookCard({ book, onEdit, onDelete }) {
  return (
    <Card className="hover:shadow-lg transition-shadow duration-300 transform hover:-translate-y-1">
      <CardHeader>
        <CardTitle className="text-xl font-bold truncate">{book.title}</CardTitle>
        <CardDescription className="text-sm text-muted-foreground">{book.author}</CardDescription>
      </CardHeader>
      <CardContent className="space-y-2">
        <p className="text-sm text-gray-500 line-clamp-3 h-16">{book.description}</p>
        <div className="grid grid-cols-2 gap-2 text-xs text-muted-foreground mt-4">
          <div className="flex flex-col">
            <span className="font-semibold">ISBN</span>
            <span>{book.isbn}</span>
          </div>
          <div className="flex flex-col text-right">
            <span className="font-semibold">Published</span>
            <span>{book.publicationYear}</span>
          </div>
          <div className="flex flex-col">
            <span className="font-semibold">Pages</span>
            <span>{book.totalPages}</span>
          </div>
          <div className="flex flex-col text-right">
            <span className="font-semibold text-primary text-base">${book.price}</span>
          </div>
        </div>
      </CardContent>
      <CardFooter className="flex justify-end space-x-2 pt-2 border-t bg-muted/20">
        <Button variant="ghost" size="sm" onClick={() => onEdit(book)}>
          <PencilIcon className="h-4 w-4 mr-2" />
          Edit
        </Button>
        <Button variant="destructive" size="sm" onClick={() => onDelete(book.id)}>
          <Trash2Icon className="h-4 w-4 mr-2" />
          Delete
        </Button>
      </CardFooter>
    </Card>
  );
}

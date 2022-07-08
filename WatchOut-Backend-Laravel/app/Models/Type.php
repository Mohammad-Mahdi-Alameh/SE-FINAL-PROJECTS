<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Type extends Model
{
    use HasFactory;
    public function infrastructalProblems()
    {
        return $this->hasMany(Infrastructal_Problem::class);
    }
}
